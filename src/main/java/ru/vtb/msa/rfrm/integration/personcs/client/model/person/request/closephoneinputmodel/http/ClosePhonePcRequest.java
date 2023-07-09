//package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.closephoneinputmodel.http;
//
//public class ClosePhonePcRequest<T> {
//    protected ClosePhonePcHeaderRequest headerRequest;
//    protected T messageRequest;
//
//    ClosePhonePcRequest(final ClosePhonePcHeaderRequest headerRequest, final T messageRequest) {
//        this.headerRequest = headerRequest;
//        this.messageRequest = messageRequest;
//    }
//
//    public static <T> PcRequestBuilder<T> builder() {
//        return new PcRequestBuilder();
//    }
//
//    public ClosePhonePcHeaderRequest getHeaderRequest() {
//        return this.headerRequest;
//    }
//
//    public T getMessageRequest() {
//        return this.messageRequest;
//    }
//
//    public void setHeaderRequest(final ClosePhonePcHeaderRequest headerRequest) {
//        this.headerRequest = headerRequest;
//    }
//
//    public void setMessageRequest(final T messageRequest) {
//        this.messageRequest = messageRequest;
//    }
//
//    public boolean equals(final Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof ClosePhonePcRequest)) {
//            return false;
//        } else {
//            ClosePhonePcRequest<?> other = (ClosePhonePcRequest) o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                Object this$headerRequest = this.getHeaderRequest();
//                Object other$headerRequest = other.getHeaderRequest();
//                if (this$headerRequest == null) {
//                    if (other$headerRequest != null) {
//                        return false;
//                    }
//                } else if (!this$headerRequest.equals(other$headerRequest)) {
//                    return false;
//                }
//
//                Object this$messageRequest = this.getMessageRequest();
//                Object other$messageRequest = other.getMessageRequest();
//                if (this$messageRequest == null) {
//                    if (other$messageRequest != null) {
//                        return false;
//                    }
//                } else if (!this$messageRequest.equals(other$messageRequest)) {
//                    return false;
//                }
//
//                return true;
//            }
//        }
//    }
//
//    protected boolean canEqual(final Object other) {
//        return other instanceof ClosePhonePcRequest;
//    }
//
//    public int hashCode() {
//        int result = 1;
//        Object $headerRequest = this.getHeaderRequest();
//        result = result * 59 + ($headerRequest == null ? 43 : $headerRequest.hashCode());
//        Object $messageRequest = this.getMessageRequest();
//        result = result * 59 + ($messageRequest == null ? 43 : $messageRequest.hashCode());
//        return result;
//    }
//
//    public String toString() {
//        ClosePhonePcHeaderRequest var10000 = this.getHeaderRequest();
//        return "PcRequest(headerRequest=" + var10000 + ", messageRequest=" + this.getMessageRequest() + ")";
//    }
//
//    public static class PcRequestBuilder<T> {
//        private ClosePhonePcHeaderRequest headerRequest;
//        private T messageRequest;
//
//        PcRequestBuilder() {
//        }
//
//        public PcRequestBuilder<T> headerRequest(final ClosePhonePcHeaderRequest headerRequest) {
//            this.headerRequest = headerRequest;
//            return this;
//        }
//
//        public PcRequestBuilder<T> messageRequest(final T messageRequest) {
//            this.messageRequest = messageRequest;
//            return this;
//        }
//
//        public ClosePhonePcRequest<T> build() {
//            return new ClosePhonePcRequest(this.headerRequest, this.messageRequest);
//        }
//
//        public String toString() {
//            return "PcRequest.PcRequestBuilder(headerRequest=" + this.headerRequest + ", messageRequest=" + this.messageRequest + ")";
//        }
//    }
//}
