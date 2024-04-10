package oauth2ResourcesServer.scrabdatas.request;

import java.util.List;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private List<T> data;
//    // 自定義靜態方法便於建立成功和失敗的響應
//    public  BaseResponse<T> success(List<T> data) {
//        BaseResponse<T> response = new BaseResponse<>();
//        response.setSuccess(true);
//        response.setData(data);
//        return response;
//    }
//
//    public BaseResponse<T> failure(String message) {
//        BaseResponse<T> response = new BaseResponse<>();
//        response.setSuccess(false);
//        response.setMessage(message);
//        return response;
//    }
}
