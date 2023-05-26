package icarius.entities;

import icarius.auth.UserClient;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;

public interface ServerActions {
    // CRUD Server Operations
    public Boolean read(GetRequest request);
    public Boolean create(UserClient user, PostRequest request);
    public Boolean update(UserClient user, PatchRequest request);
    public Boolean delete(UserClient user, DeleteRequest request);
}
