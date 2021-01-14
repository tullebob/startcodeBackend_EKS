
package dto;

public class DigitalOceanResponseDTO {
    private Object droplets;

    public DigitalOceanResponseDTO(Object droplets) {
        this.droplets = droplets;
    }

    public Object getDroplets() {
        return droplets;
    }
}
