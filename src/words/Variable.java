package words;

class Variable {
    private String name;
    private String type;
    Variable(String name,String type){
        this.name=name;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


}
