
package dto;

public class MovieRequestDTO {
    
    private String query;

    public MovieRequestDTO(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    } 
}
