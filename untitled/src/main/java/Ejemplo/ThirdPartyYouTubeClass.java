package Ejemplo;

import java.util.HashMap;

/**
 * Implementación del Servicio Real.
 * Esta clase simula ser la implementación concreta de la API de YouTube.
 * Sus métodos simulan la latencia de la red, lo que hace que las llamadas sean "costosas" en tiempo.
 * El objetivo del patrón Proxy en este caso será optimizar estas llamadas.
 */
public class ThirdPartyYouTubeClass implements ThirdPartyYouTubeLib {

    /**
     * Descarga la lista de videos populares. Simula una conexión lenta.
     */

    @Override
    public HashMap<String, Video> popularVideos() {
        connectToServer("http://www.youtube.com");
        return getRandomVideos();
    }

    /**
     * Descarga la información de un video específico. Simula una conexión lenta.
     */

    @Override
    public Video getVideo(String videoId) {
        connectToServer("http://www.youtube.com/" + videoId);
        return getSomeVideo(videoId);
    }

    // -----------------------------------------------------------------------
    // MÉTODOS DE SIMULACIÓN DE RED.
    // No son parte del patrón en sí, pero ayudan a demostrar su utilidad.
    // Simulan lo lento que puede ser comunicarse con un servicio remoto.

    private int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * Simula una latencia de red variable para hacer el ejemplo más realista.
     * Detiene el hilo de ejecución por un tiempo aleatorio.
     */

    private void experienceNetworkLatency() {
        int randomLatency = random(5, 10);
        for (int i = 0; i < randomLatency; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Simula el proceso de conexión a un servidor remoto.
     * Imprime mensajes y añade una latencia.
     */
    private void connectToServer(String server) {
        System.out.print("Connecting to " + server + "... ");
        experienceNetworkLatency();
        System.out.print("Connected!" + "\n");
    }

    /**
     * Simula la descarga de la lista de videos populares.
     */
    private HashMap<String, Video> getRandomVideos() {
        System.out.print("Downloading populars... ");

        experienceNetworkLatency();
        HashMap<String, Video> hmap = new HashMap<String, Video>();
        hmap.put("catzzzzzzzzz", new Video("sadgahasgdas", "Catzzzz.avi"));
        hmap.put("mkafksangasj", new Video("mkafksangasj", "Dog play with ball.mp4"));
        hmap.put("dancesvideoo", new Video("asdfas3ffasd", "Dancing video.mpq"));
        hmap.put("dlsdk5jfslaf", new Video("dlsdk5jfslaf", "Barcelona vs RealM.mov"));
        hmap.put("3sdfgsd1j333", new Video("3sdfgsd1j333", "Programing lesson#1.avi"));

        System.out.print("Done!" + "\n");
        return hmap;
    }

    /**
     * Simula la descarga de un único video.
     */
    private Video getSomeVideo(String videoId) {
        System.out.print("Downloading video... ");

        experienceNetworkLatency();
        Video video = new Video(videoId, "Some video title");

        System.out.print("Done!" + "\n");
        return video;
    }
}
