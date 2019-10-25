package vwmin.coolq.network;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

abstract class ParameterHandler<T> {

    public abstract void apply(List<BasicNameValuePair> pairs, T value);

    static final class Field extends ParameterHandler<String> {
        private final String fieldName;
        public Field(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public void apply(List<BasicNameValuePair> pairs, String value) {
            pairs.add(new BasicNameValuePair(fieldName, value));
        }

        public String getFieldName(){
            return fieldName;
        }
    }

    static final class Query extends ParameterHandler<String> {
        private final String queryName;
        public Query(String queryName){
            this.queryName = queryName;
        }

        @Override
        public void apply(List<BasicNameValuePair> pairs, String value) {
            pairs.add(new BasicNameValuePair(queryName, value));
        }
    }

    static final class Json extends ParameterHandler<Object> {
        private final int index;
        public Json(int index){this.index = index;}

        public int getIndex() {
            return index;
        }

        @Override
        public void apply(List<BasicNameValuePair> pairs, Object value) {

        }
    }
}
