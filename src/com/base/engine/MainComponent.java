package com.base.engine;

public class MainComponent {

    private int m_fpsLimit;
    private long m_fpsRefreshTime;

    private Game m_game;

    private boolean m_isRunning;

    public MainComponent(int width, int height, String title) {
        m_fpsLimit = 60;
        m_fpsRefreshTime = (long)(1.0 * Time.SECOND);

        Window.createWindow(width, height, title);
        RenderUtil.initGraphics();
        System.out.println(RenderUtil.getOpenGLVersion());

        m_game = new Game();

        m_isRunning = false;
    }

    public void start() {
        if (m_isRunning)
            return;

        m_isRunning = true;
        run();
    }

    private void run() {
        int frames = 0;
        long fpsTime = 0;

        long startTime = Time.getTime();
        long frameTime = Time.SECOND / (long) m_fpsLimit;
        long unprocessedTime = 0;

        while (m_isRunning) {
            long endTime = Time.getTime();
            long passedTime = endTime - startTime;
            startTime = endTime;

            unprocessedTime += passedTime;
            fpsTime += passedTime;

            boolean render = false;

            if (unprocessedTime >= frameTime) {
                long sceneTime = frameTime * (unprocessedTime / frameTime);
                unprocessedTime -= sceneTime;

                render = true;

                if (Window.isCloseRequested())
                    stop();

                Time.setDelta((double)sceneTime / (double)Time.SECOND);

                m_game.input();
                Input.update();
                m_game.update();
            }

            if (fpsTime >= m_fpsRefreshTime) {
                System.out.printf("%.2f\n", (double)frames/((double)fpsTime/(double)Time.SECOND));
                fpsTime = 0;
                frames = 0;
            }

            if (render) {
                render();
                frames++;
            } else {
                try {
                    Thread.sleep(0, 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    private void render() {
        RenderUtil.clearScreen();
        m_game.render();
        Window.render();
    }

    public void stop() {
        if (!m_isRunning)
            return;

        m_isRunning = false;
    }

    private void cleanUp() {
        Window.dispose();
    }

    public int getFPSLimit() {
        return m_fpsLimit;
    }

    public void setFPSLimit(int fpsLimit) {
        this.m_fpsLimit = fpsLimit;
    }

    public static void main(String[] args) {
        MainComponent engine = new MainComponent(800, 600, "3D Engine");
        engine.start();
    }
}