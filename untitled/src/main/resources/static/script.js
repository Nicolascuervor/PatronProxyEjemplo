document.addEventListener('DOMContentLoaded', () => {
    const directButton = document.getElementById('direct-button');
    const proxyButton = document.getElementById('proxy-button');
    const outputArea = document.getElementById('output');

    const fetchData = async (endpoint) => {
        outputArea.textContent = 'Ejecutando prueba, por favor espera...';
        try {
            const response = await fetch(endpoint);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const text = await response.text();
            outputArea.textContent = text;
        } catch (error) {
            outputArea.textContent = `Error al conectar con el servidor: ${error.message}\n\nAsegúrate de que el servidor Java se está ejecutando.`;
        }
    };

    directButton.addEventListener('click', () => {
        fetchData('/test-directo');
    });

    proxyButton.addEventListener('click', () => {
        fetchData('/test-proxy');
    });
});