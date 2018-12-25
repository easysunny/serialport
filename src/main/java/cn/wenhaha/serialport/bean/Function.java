package cn.wenhaha.serialport.bean;

public class Function {
    private String name;
    private String address;
    private String className;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Function{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
