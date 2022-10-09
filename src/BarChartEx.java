import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartEx extends JFrame {

    public BarChartEx(int[] data) {

        initUI(data);
    }

    private void initUI(int[] data) {

        CategoryDataset dataset = createDataset(data);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset(int[] data) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i=0;i<data.length;i++){
            dataset.setValue(data[i], "count", i+1+"");
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "Triangular Distribution count",
                "",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

}

