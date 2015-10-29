package com.mstack.toolstracker.model;

import java.util.List;

/**
 * Created by pitipong on 26/10/2558.
 */
public class TrackingModel {

    /**
     * resultCode : 200
     * resultText : Tracking successfully !
     * resultData : [{"order":1,"state":"T","label":"Service Code","value":"TH2015-10-00010"},{"order":2,"state":"T","label":"Service Type","value":"Product Warranty - MVI"},{"order":3,"state":"T","label":"Tools Model","value":"PF4000-G-HW"},{"order":4,"state":"T","label":"Serial Number","value":"D667144"},{"order":5,"state":"T","label":"Tools Type","value":"Controller"},{"order":6,"state":"T","label":"Customer","value":"MITSUBISHI MOTORS (THAILAND) CO.,LTD"},{"order":7,"state":"T","label":"Jobs No.","value":"0155600988"},{"order":8,"state":"T","label":"Worksheet","value":""},{"order":9,"state":"T","label":"Register Time","value":"2015-10-01T09:33:06.000Z"},{"order":10,"state":"T","label":"TAT All","value":15},{"order":ic_11,"state":"T","label":"TAT Now","value":7},{"order":12,"state":"T","label":"Service Status","value":"Wait for Part"},{"order":13,"state":"T","label":"Last Update","value":"2015-10-22T05:14:56.000Z"},{"order":14,"state":"F","label":"Condition #1","value":"R"},{"order":15,"state":"F","label":"Condition #2","value":"Y"},{"order":16,"state":"F","label":"Condition #3","value":"ic_21"}]
     */

    private int resultCode;
    private String resultText;
    /**
     * order : 1
     * state : T
     * label : Service Code
     * value : TH2015-10-00010
     */

    private List<ResultDataEntity> resultData;

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public void setResultData(List<ResultDataEntity> resultData) {
        this.resultData = resultData;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultText() {
        return resultText;
    }

    public List<ResultDataEntity> getResultData() {
        return resultData;
    }

    public static class ResultDataEntity {
        private int order;
        private String state;
        private String label;
        private String value;

        public void setOrder(int order) {
            this.order = order;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getOrder() {
            return order;
        }

        public String getState() {
            return state;
        }

        public String getLabel() {
            return label;
        }

        public String getValue() {
            return value;
        }
    }
}
