package Ejemplo;

/*Esta clase representa un video que contiene la informacion que obtendriamos de la API de youtube*/

public class Video {
    public String id;
    public String title;
    public String data;

    /*Constructor*/
    Video(String id, String title) {
        this.id = id;
        this.title = title;
        this.data = "Random video";
    }
}
