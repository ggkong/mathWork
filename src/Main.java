import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;


public class Main{
    // 低限 为 a, 众数为 c， 上限为 b, 生成数据集合
    public List<Double> triangularDistribution(double a, double b, double c, int numOfSample) {
        double F = (c - a) / (b - a);
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < numOfSample; i++) {
            double rand = Math.random();
            double temp = 0.0;
            if(rand < F){
                temp = a + Math.sqrt(rand*(b-a)*(c-a));
            }else {
                temp = b - Math.sqrt((1-rand)*(b-a)*(b-c));
            }
            result.add(temp);
        }
        return result;
    }

    // 将数据集合生成带Xy 坐标的样本点, 以及柱状图数据
    public Combine sampleToXyPoints(List<Double> sample, double gapIn, double incrementalIn, double top, double bottom){
        System.out.println(sample.size());
        Combine combine = new Combine();
        sample.sort(Comparator.naturalOrder());
        double gap = gapIn;
        double Y = -1*incrementalIn;
        double incremental =incrementalIn;

        List<XyPointMy> XyPoints = new ArrayList<>();
        int lenOfArray = (int) ((top - bottom)/gap);
        int[] numOfBar = new int[lenOfArray];
        double[] dataTest = new double[lenOfArray];
        int count = 0;
        int index = 0;
        int indexOfBar = 0;
        for (double a = bottom+gap; a <= top; a+=gap){
            for(int j = index;j<sample.size();j++){
                if(sample.get(j) <= a){
                    XyPointMy xy = new XyPointMy(new BigDecimal(Y+incremental).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue(), new BigDecimal(sample.get(j)).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
                    XyPoints.add(xy);
                    incremental+=incrementalIn;
                    count++;
                }else{
                    // 区间结束
                    incremental=incrementalIn;
                    index = j;
                    numOfBar[indexOfBar] = count;
                    dataTest[indexOfBar] = (double) count / (double) sample.size();
                    indexOfBar++;
                    count = 0;
                    break;
                }
            }
        }

//        for (double x:dataTest){
//            System.out.println(x);
//        }
        combine.setObj1(XyPoints);
        combine.setObj2(numOfBar);
        combine.setObj3(dataTest);
        return combine;

    }

    // 计算累计分布函数坐标点
    public HashMap<Double,Double> cumulative(double gap, double[] dataCum){
        double tempCum = dataCum[0];
        HashMap<Double,Double> points = new HashMap<>();
        int len = dataCum.length;
        for (int i=0;i<len-1;i++){
            points.put(gap*i,tempCum);
            tempCum = tempCum + dataCum[i+1];

        }
        return points;
    }

    public void data2Csv(List<Double> result) throws IOException {
        FileOutputStream fos = new FileOutputStream("./triangular_data.csv");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
        CSVFormat csvFormat = CSVFormat.DEFAULT;
        CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
        for (double x:result) {
            csvPrinter.printRecord(x);
        }
        csvPrinter.flush();
        csvPrinter.close();

    }


    public static void main(String[] args) throws IOException {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入a,b,c 以及 样本数量");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();
        int numOfSample = scanner.nextInt();
        List<Double> sample = main.triangularDistribution(a,b,c,numOfSample);
        main.data2Csv(sample);
        Combine combine = main.sampleToXyPoints(sample, 1, 0.1, b, a);
        HashMap<Double,Double> points = main.cumulative(1, combine.getObj3());

        SwingUtilities.invokeLater(() -> {
            BarChartEx ex = new BarChartEx(combine.getObj2());
            ex.setVisible(true);
        });

        SwingUtilities.invokeLater(() -> {
            BarProbability ex = new BarProbability(combine.getObj3());
            ex.setVisible(true);
        });
        SwingUtilities.invokeLater(() -> {
            LineCharts ex = new LineCharts(points);
            ex.setVisible(true);
        });
    }

}