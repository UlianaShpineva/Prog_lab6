package network;

import models.StudyGroup;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class Response implements Serializable {
    private final ResponseStatus status;
    private String response = "";
    private Collection<StudyGroup> collection;

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public Response(ResponseStatus status, String response) {
        this.status = status;
        this.response = response.trim();
    }

    public Response(ResponseStatus status, String response, Collection<StudyGroup> collection) {
        this.status = status;
        this.response = response.trim();
        this.collection = collection.stream()
                .sorted(Comparator.comparing(StudyGroup::getId))
                .toList();
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public Collection<StudyGroup> getCollection() {
        return collection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response1 = (Response) o;

        if (status != response1.status) return false;
        if (!Objects.equals(response, response1.response)) return false;
        return Objects.equals(collection, response1.collection);
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (response != null ? response.hashCode() : 0);
        result = 31 * result + (collection != null ? collection.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", response='" + response + '\'' +
                ", collection=" + collection +
                '}';
    }
}
