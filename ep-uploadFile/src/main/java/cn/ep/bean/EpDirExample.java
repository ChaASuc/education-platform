package cn.ep.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EpDirExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EpDirExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDirIdIsNull() {
            addCriterion("dir_id is null");
            return (Criteria) this;
        }

        public Criteria andDirIdIsNotNull() {
            addCriterion("dir_id is not null");
            return (Criteria) this;
        }

        public Criteria andDirIdEqualTo(Long value) {
            addCriterion("dir_id =", value, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdNotEqualTo(Long value) {
            addCriterion("dir_id <>", value, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdGreaterThan(Long value) {
            addCriterion("dir_id >", value, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dir_id >=", value, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdLessThan(Long value) {
            addCriterion("dir_id <", value, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdLessThanOrEqualTo(Long value) {
            addCriterion("dir_id <=", value, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdIn(List<Long> values) {
            addCriterion("dir_id in", values, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdNotIn(List<Long> values) {
            addCriterion("dir_id not in", values, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdBetween(Long value1, Long value2) {
            addCriterion("dir_id between", value1, value2, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirIdNotBetween(Long value1, Long value2) {
            addCriterion("dir_id not between", value1, value2, "dirId");
            return (Criteria) this;
        }

        public Criteria andDirNameIsNull() {
            addCriterion("dir_name is null");
            return (Criteria) this;
        }

        public Criteria andDirNameIsNotNull() {
            addCriterion("dir_name is not null");
            return (Criteria) this;
        }

        public Criteria andDirNameEqualTo(String value) {
            addCriterion("dir_name =", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotEqualTo(String value) {
            addCriterion("dir_name <>", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameGreaterThan(String value) {
            addCriterion("dir_name >", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameGreaterThanOrEqualTo(String value) {
            addCriterion("dir_name >=", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameLessThan(String value) {
            addCriterion("dir_name <", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameLessThanOrEqualTo(String value) {
            addCriterion("dir_name <=", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameLike(String value) {
            addCriterion("dir_name like", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotLike(String value) {
            addCriterion("dir_name not like", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameIn(List<String> values) {
            addCriterion("dir_name in", values, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotIn(List<String> values) {
            addCriterion("dir_name not in", values, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameBetween(String value1, String value2) {
            addCriterion("dir_name between", value1, value2, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotBetween(String value1, String value2) {
            addCriterion("dir_name not between", value1, value2, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirTypeIsNull() {
            addCriterion("dir_type is null");
            return (Criteria) this;
        }

        public Criteria andDirTypeIsNotNull() {
            addCriterion("dir_type is not null");
            return (Criteria) this;
        }

        public Criteria andDirTypeEqualTo(String value) {
            addCriterion("dir_type =", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeNotEqualTo(String value) {
            addCriterion("dir_type <>", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeGreaterThan(String value) {
            addCriterion("dir_type >", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dir_type >=", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeLessThan(String value) {
            addCriterion("dir_type <", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeLessThanOrEqualTo(String value) {
            addCriterion("dir_type <=", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeLike(String value) {
            addCriterion("dir_type like", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeNotLike(String value) {
            addCriterion("dir_type not like", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeIn(List<String> values) {
            addCriterion("dir_type in", values, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeNotIn(List<String> values) {
            addCriterion("dir_type not in", values, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeBetween(String value1, String value2) {
            addCriterion("dir_type between", value1, value2, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeNotBetween(String value1, String value2) {
            addCriterion("dir_type not between", value1, value2, "dirType");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}