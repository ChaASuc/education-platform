package cn.ep.courseenum;

public enum ChapterEnum {

    INVALID_STATUS(0),
    VALID_STATUS(1);

    int value;
    ChapterEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
