package Ejemplo;

import java.util.HashMap;

/**
 * La clase Proxy.
 * Actúa como un intermediario entre el Cliente (YouTubeDownloader) y el Servicio Real (ThirdPartyYouTubeClass).
 * Su principal valor añadido en este ejemplo es el "caching": guarda los resultados de las operaciones
 * costosas para devolverlos rápidamente en solicitudes futuras.
 */

public class YouTubeCacheProxy implements ThirdPartyYouTubeLib {
    // Referencia al objeto del Servicio Real. El proxy necesita delegarle las llamadas
    // cuando los datos no están en la caché.
    private ThirdPartyYouTubeLib youtubeService;

    // Caché para la lista de videos populares.
    private HashMap<String, Video> cachePopular = new HashMap<String, Video>();
    // Caché para videos individuales que ya han sido descargados.
    private HashMap<String, Video> cacheAll = new HashMap<String, Video>();

    /**
     * El constructor del proxy crea una instancia del Servicio Real.
     * El proxy "posee" o "envuelve" al objeto real.
     */
    public YouTubeCacheProxy() {
        this.youtubeService = new ThirdPartyYouTubeClass();
    }

    /**
     * Implementación del método para obtener videos populares, con lógica de caché.
     */
    @Override
    public HashMap<String, Video> popularVideos() {
        // Si la caché de videos populares está vacía...
        if (cachePopular.isEmpty()) {
            // ...entonces llama al servicio real para obtener los datos (operación costosa).
            cachePopular = youtubeService.popularVideos();
        } else {
            // ...de lo contrario, simplemente informa que los datos se obtuvieron de la caché.
            System.out.println("Retrieved list from cache.");
        }
        // Devuelve los datos (ya sea recién obtenidos o desde la caché).
        return cachePopular;
    }

    /**
     * Implementación del método para obtener un video, con lógica de caché.
     */
    @Override
    public Video getVideo(String videoId) {
        // Intenta obtener el video desde la caché de videos individuales.
        Video video = cacheAll.get(videoId);

        // Si el video no se encuentra en la caché (es la primera vez que se pide)...
        if (video == null) {
            // ...entonces llama al servicio real para descargarlo (operación costosa).
            video = youtubeService.getVideo(videoId);
            // Y lo guarda en la caché para futuras solicitudes.
            cacheAll.put(videoId, video);
        } else {
            // ...de lo contrario, informa que el video se obtuvo de la caché.
            System.out.println("Retrieved video '" + videoId + "' from cache.");
        }
        // Devuelve el video (recién descargado o desde la caché).
        return video;
    }

    /**
     * Método para limpiar la caché. Útil para pruebas o para forzar la recarga de datos.
     */
    public void reset() {
        cachePopular.clear();
        cacheAll.clear();
    }
}
