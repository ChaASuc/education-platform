package cn.ep.courseenum;

public enum WatchRecordEnum {

    INVALID_STATUS(0),
    VALID_STATUS(1);

    int value;
    WatchRecordEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
