package com.base.engine.core;

public class Matrix4f {

    private float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

    public Matrix4f initIdentity() {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f initTranslation(Vector3f r) {
        return this.initTranslation(r.getX(), r.getY(), r.getZ());
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = y;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f initRotationDeg(Vector3f r) {
        return this.initRotationDeg(r.getX(), r.getY(), r.getZ());
    }

    public Matrix4f initRotationDeg(float x, float y, float z) {
        return this.initRotationRad((float) Math.toRadians(x), (float) Math.toRadians(y), (float) Math.toRadians(z));
    }

    public Matrix4f initRotationRad(Vector3f r) {
        return this.initRotationRad(r.getX(), r.getY(), r.getZ());
    }

    public Matrix4f initRotationRad(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rx.m[0][0] = 1; rx.m[0][1] = 0;                  rx.m[0][2] = 0;                   rx.m[0][3] = 0;
        rx.m[1][0] = 0; rx.m[1][1] = (float)Math.cos(x); rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
        rx.m[2][0] = 0; rx.m[2][1] = (float)Math.sin(x); rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
        rx.m[3][0] = 0; rx.m[3][1] = 0;                  rx.m[3][2] = 0;                   rx.m[3][3] = 1;

        ry.m[0][0] = (float)Math.cos(y);  ry.m[0][1] = 0; ry.m[0][2] = (float)Math.sin(y); ry.m[0][3] = 0;
        ry.m[1][0] = 0;                   ry.m[1][1] = 1; ry.m[1][2] = 0;                  ry.m[1][3] = 0;
        ry.m[2][0] = -(float)Math.sin(y); ry.m[2][1] = 0; ry.m[2][2] = (float)Math.cos(y); ry.m[2][3] = 0;
        ry.m[3][0] = 0;                   ry.m[3][1] = 0; ry.m[3][2] = 0;                  ry.m[3][3] = 1;

        rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); rz.m[0][2] = 0; rz.m[0][3] = 0;
        rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  rz.m[1][2] = 0; rz.m[1][3] = 0;
        rz.m[2][0] = 0;                  rz.m[2][1] = 0;                   rz.m[2][2] = 1; rz.m[2][3] = 0;
        rz.m[3][0] = 0;                  rz.m[3][1] = 0;                   rz.m[3][2] = 0; rz.m[3][3] = 1;

        m = rz.getMul(ry.getMul(rx)).getArray();

        return this;
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
        Vector3f f = forward.getNormalized();
        Vector3f u = up.getNormalized();
        Vector3f r = right.getNormalized();

        m[0][0] = r.getX(); m[0][1] = r.getY(); m[0][2] = r.getZ(); m[0][3] = 0;
        m[1][0] = u.getX(); m[1][1] = u.getY(); m[1][2] = u.getZ(); m[1][3] = 0;
        m[2][0] = -f.getX(); m[2][1] = -f.getY(); m[2][2] = -f.getZ(); m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up) {
        Vector3f right = forward.getCross(up);
        return initRotation(forward, up, right);
    }

    public Matrix4f initRotation(Quaternion rotation) {
        return initRotation(rotation.getForward(), rotation.getUp());
    }

    public Matrix4f initScale(Vector3f r) {
        return this.initScale(r.getX(), r.getY(), r.getZ());
    }

    public Matrix4f initScale(float x, float y, float z) {
        m[0][0] = x; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = y; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = z; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    /**
     * @param fov full angle of field of view in radians (Y-axis)
     * @param aspectRatio aspect ratio of window
     * @param zNear near clipping plane
     * @param zFar far clipping plane
     * @return perspective projection
     */
    public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
        float tanHalfFOV = (float) Math.tan(fov/2.0f);
        float zRange = zFar - zNear;

        m[0][0] = 1.0f/(tanHalfFOV * aspectRatio); m[0][1] = 0;               m[0][2] = 0;                    m[0][3] = 0;
        m[1][0] = 0;                               m[1][1] = 1.0f/tanHalfFOV; m[1][2] = 0;                    m[1][3] = 0;
        m[2][0] = 0;                               m[2][1] = 0;               m[2][2] = -(zFar+zNear)/zRange; m[2][3] = -2*zFar*zNear/zRange;
        m[3][0] = 0;                               m[3][1] = 0;               m[3][2] = -1;                   m[3][3] = 0;

        return this;
    }

    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
        m[0][0] = 2/(right - left); m[0][1] = 0;                m[0][2] = 0;               m[0][3] = -(left + right)/(right - left);
        m[1][0] = 0;                m[1][1] = 2/(top - bottom); m[1][2] = 0;               m[1][3] = -(top + bottom)/(top - bottom);
        m[2][0] = 0;                m[2][1] = 0;                m[2][2] = -2/(far - near); m[2][3] = -(near + far)/(far - near);
        m[3][0] = 0;                m[3][1] = 0;                m[3][2] = 0;               m[3][3] = 1;

        return this;
    }

    public Matrix4f getMul(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j, m[i][0] * r.get(0, j) +
                              m[i][1] * r.get(1, j) +
                              m[i][2] * r.get(2, j) +
                              m[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public void set(int x, int y, float value) {
        m[x][y] = value;
    }

    public float[][] getArray() {
        float[][] res = new float[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                res[i][j] = m[i][j];
        return res;
    }

    public void setM(float[][] m) {
        this.m = m;
    }
}
