package cn.ep.courseenum;

public enum CourseEnum {


    INVALID_STATUS(0),
    UNCHECKED_STATUS(3),
    CHECK_NOT_PASS(2),
    CHECK_PASS(1);

    int value;
    CourseEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
