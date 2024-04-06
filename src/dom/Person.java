package dom;

/**
 * 存储人物信息
 */
public class Person {
    private String name;
    private int imgID;

    private double weight;


    public Person() {
    }

    public Person(String name, int imgID, double weight) {
        this.name = name;
        this.imgID = imgID;
        this.weight = weight;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return imgID
     */
    public int getImgID() {
        return imgID;
    }

    /**
     * 设置
     * @param imgID
     */
    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    /**
     * 获取
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * 设置
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String toString() {
        return "Person{name = " + name + ", imgID = " + imgID + ", weight = " + weight + "}";
    }
}
