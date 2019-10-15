package cn.ep.courseenum;

public enum CheckEnum {

    //审核结果枚举
    UNCHECKED_STATUS(0),
    CHECK_NOT_PASS(1),
    CHECK_PASS(2),
//审核类型枚举
    CHECK_VIDEO(0),  //审核视频
    CHECK_COURSE(1), //审核课程内容
    CHECK_COURSE_KIND(2); //审核种类内容

    int value;
    CheckEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
