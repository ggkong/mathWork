import java.util.List;

public class Combine {
    List<XyPointMy> obj1;
    int[] obj2;
    double[] obj3;

    public Combine(List<XyPointMy> obj1, int[] obj2, double[] obj3) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.obj3 = obj3;
    }

    public Combine() {
    }

    public void setObj1(List<XyPointMy> obj1) {
        this.obj1 = obj1;
    }

    public void setObj2(int[] obj2) {
        this.obj2 = obj2;
    }

    public void setObj3(double[] obj3) {
        this.obj3 = obj3;
    }

    public List<XyPointMy> getObj1() {
        return obj1;
    }

    public int[] getObj2() {
        return obj2;
    }

    public double[] getObj3() {
        return obj3;
    }
}

