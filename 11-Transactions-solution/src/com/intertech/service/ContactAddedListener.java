package com.intertech.service;

import javax.swing.JOptionPane;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ContactAddedListener implements
                ApplicationListener<ApplicationEvent> {

    private Boolean useSwingAlert;

    public Boolean getUseSwingAlert() {
        return useSwingAlert;
    }

    public void setUseSwingAlert(Boolean useSwingAlert) {
        this.useSwingAlert = useSwingAlert;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        String message = "";
        if (event instanceof ContactAddedEvent) {
            message = ((ContactAddedEvent) event).getContact() + " was just added!";
            if (useSwingAlert) {
                JOptionPane.showMessageDialog(null, message, "New Contact Added", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Listener: " + message);
            }
        }
    }
}
