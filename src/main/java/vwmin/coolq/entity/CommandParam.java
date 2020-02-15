package vwmin.coolq.entity;

public class CommandParam {
    private String name;
    private String value;

    public CommandParam(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String name(){
        return this.name;
    }

    public String value(){
        return this.value;
    }
}
