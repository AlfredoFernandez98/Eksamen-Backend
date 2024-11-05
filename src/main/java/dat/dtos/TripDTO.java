package dat.dtos;
import dat.entities.Trip;
import dat.enums.TripCategory;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TripDTO {

    private Long id;
    private LocalDate starttime;
    private LocalDate endtime;
    private String startposition;
    private String name;

    private double price;

    private TripCategory category;

    private GuideDTO guide;

    public TripDTO() {

    }

    public TripDTO(LocalDate starttime, LocalDate endtime, String startposition, String name, double price, TripCategory category, GuideDTO guide) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;

    }

    public TripDTO(Trip trip){
        this.id = trip.getId();
        this.starttime = trip.getStarttime();
        this.endtime = trip.getEndtime();
        this.startposition = trip.getStartposition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        // Kun initialiser guide, hvis trip.getGuide() ikke er null
        if(trip.getGuide() != null){
            this.guide = new GuideDTO(trip.getGuide());
        }
    }

    public TripDTO(Long id, LocalDate now, LocalDate localDate, String updatedBeachResort, String updatedBeachAdventure, double v, TripCategory tripCategory, GuideDTO guideDTO) {
    }
}
