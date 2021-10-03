package com.sanotes.saNotesMongo.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "note")
public class NoteModel {

    @Id
    private String id;

    private String topic;
    private String text;

    public NoteModel() {
    }

    public NoteModel(String topic, String text) {

        this.topic = topic;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
