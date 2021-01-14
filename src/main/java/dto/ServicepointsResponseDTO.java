package dto;

public class ServicepointsResponseDTO {
    private PostnordResponseDTO postnord;
    private WeatherResponseDTO weather;

    public ServicepointsResponseDTO(PostnordResponseDTO postnord, WeatherResponseDTO weather) {
        this.postnord = postnord;
        this.weather = weather;
    }

    public PostnordResponseDTO getPostnord() {
        return postnord;
    }

    public void setPostnord(PostnordResponseDTO postnord) {
        this.postnord = postnord;
    }

    public WeatherResponseDTO getWeather() {
        return weather;
    }

    public void setWeather(WeatherResponseDTO weather) {
        this.weather = weather;
    }
}
