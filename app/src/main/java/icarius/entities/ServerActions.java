package icarius.entities;

import icarius.auth.User;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;

public interface ServerActions {
    // CRUD Server Operations
    public Boolean read(GetRequest request);
    public Boolean create(User user, PostRequest request);
    public Boolean update(User user, PatchRequest request);
    public Boolean delete(User user, DeleteRequest request);
}
