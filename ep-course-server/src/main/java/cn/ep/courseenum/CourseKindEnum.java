package cn.ep.courseenum;

public enum CourseKindEnum {
    INVALID_STATUS(0),
    VALID_STATUS(1),

    ROOT(0);

    int value;
    CourseKindEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
