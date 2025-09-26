package Ejemplo;

import java.util.HashMap;

/**
 * La clase Cliente.
 * Esta clase es la que utiliza el servicio para realizar su trabajo (en este caso, "renderizar" páginas de video).
 * Es importante destacar que esta clase no sabe si está trabajando con el servicio real o con un proxy.
 * Depende únicamente de la interfaz `ThirdPartyYouTubeLib`.
 */
public class YouTubeDownloader {
    // El cliente tiene una referencia a un objeto que implementa la interfaz del servicio.
    // Puede ser una instancia de ThirdPartyYouTubeClass o de YouTubeCacheProxy.
    private ThirdPartyYouTubeLib api;

    /**
     * El cliente recibe un objeto de servicio a través de su constructor (Inyección de Dependencias).
     * Esto le permite ser flexible y trabajar con cualquier implementación de la interfaz.
     * @param api Una implementación de ThirdPartyYouTubeLib (el servicio real o un proxy).
     */
    public YouTubeDownloader(ThirdPartyYouTubeLib api) {
        this.api = api;
    }

    /**
     * Simula la renderización de la página de un video específico.
     * Llama al método `getVideo` del objeto de servicio (que podría ser el proxy).
     * @param videoId El ID del video a mostrar.
     */
    public void renderVideoPage(String videoId) {
        // El cliente simplemente pide el video. No sabe si vendrá de la caché o de una descarga real.
        Video video = api.getVideo(videoId);
        System.out.println("\n-------------------------------");
        System.out.println("Video page (imagine fancy HTML)");
        System.out.println("ID: " + video.id);
        System.out.println("Title: " + video.title);
        System.out.println("Video: " + video.data);
        System.out.println("-------------------------------");
        System.out.println();
    }

    /**
     * Simula la renderización de la página de videos populares.
     * Llama al método `popularVideos` del objeto de servicio.
     */
    public void renderPopularVideos() {
        // Lo mismo ocurre aquí: el cliente pide la lista y no se preocupa por el origen de los datos.
        HashMap<String, Video> list = api.popularVideos();
        System.out.println("\n-------------------------------");
        System.out.println("Most popular videos on YouTube (imagine fancy HTML)");
        for (Video video : list.values()) {
            System.out.println("ID: " + video.id + " / Title: " + video.title);
        }
        System.out.println("-------------------------------");
        System.out.println();
    }
}
