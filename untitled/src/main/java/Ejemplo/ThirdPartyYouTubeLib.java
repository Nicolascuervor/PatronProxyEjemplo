package Ejemplo;

import java.util.HashMap;

/**
 * Interfaz del Servicio Remoto.
 * En este ejemplo, simulamos una interfaz para la API de YouTube.
 *
 * El patrón Proxy se basa en que tanto el "Servicio Real" como el "Proxy"
 * implementen la misma interfaz. De esta manera, el cliente puede tratar
 * al proxy como si fuera el servicio real, sin saber que hay una capa
* intermedia.
 */
public interface ThirdPartyYouTubeLib {
    /**
     * Obtiene una lista de los videos más populares.
     * En una aplicación real, esto implicaría una solicitud de red costosa.
     * @return Un HashMap donde la clave es el ID del video y el valor es un objeto Video.
     */

    HashMap<String, Video> popularVideos();

    /**
     * Obtiene la información de un video específico por su ID.
     * También simula una solicitud de red costosa.
     * @param videoId El identificador único del video.
     * @return Un objeto Video con la información del video solicitado.
     */
    Video getVideo(String videoId);

}
