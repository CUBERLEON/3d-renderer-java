package com.cuberleon.engine.components;

import com.cuberleon.engine.core.Vector3f;
import com.cuberleon.engine.rendering.Attenuation;
import com.cuberleon.engine.rendering.shaders.Shader;

public class SpotLight extends PointLight {

    protected float m_cutoff;

    public SpotLight(SpotLight r) {
        this(r.getColor(), r.getIntensity(), r.getAttenuation(), r.getRange(), r.getCutoff());
    }

    public SpotLight(Vector3f color, float intensity, Attenuation attenuation, float cutoff) {
        super(color, intensity, attenuation);
        this.m_cutoff = cutoff;
        setShader(new Shader("forward-spot"));
    }

    public SpotLight(PointLight pointLight, float cutoff) {
        this(pointLight.getColor(), pointLight.getIntensity(), pointLight.getAttenuation(), pointLight.getRange(), cutoff);
    }

    protected SpotLight(Vector3f color, float intensity, Attenuation attenuation, float range, float cutoff) {
        super(color, intensity, attenuation, range);
        this.m_cutoff = cutoff;
        setShader(new Shader("forward-spot"));
    }

    public Vector3f getDirection() {
        return getTransform().getRealRotation().getForward();
    }

    public float getCutoff() {
        return m_cutoff;
    }

    public void setCutoff(float cutoff) {
        this.m_cutoff = cutoff;
    }
}
