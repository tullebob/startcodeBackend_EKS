package dto;

public class PostnordResponseDTO {
    private Object servicePointInformationResponse;

    public PostnordResponseDTO(Object servicePointInformationResponse) {
        this.servicePointInformationResponse = servicePointInformationResponse;
    }

    public Object getServicePointInformationResponse() {
        return servicePointInformationResponse;
    }

    public void setServicePointInformationResponse(Object servicePointInformationResponse) {
        this.servicePointInformationResponse = servicePointInformationResponse;
    }
}
