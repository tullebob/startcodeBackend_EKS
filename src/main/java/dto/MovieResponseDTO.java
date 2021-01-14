
package dto;

public class MovieResponseDTO {
    
    private Object results;

    public MovieResponseDTO(Object results) {
        this.results = results;
    }

    public Object getMovie() {
        return results;
    }

    public void setMovie(Object results) {
        this.results = results;
    }
}
