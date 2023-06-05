package icarius.entities;

import icarius.auth.UserClient;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;

public interface ServerActions {
    // CRUD Server Operations
    /**
     * Read object from server
     * 
     * @param request
     * @return TRUE if read successfully, else FALSE
     */
    public Boolean read(GetRequest request);

    /**
     * Create object on server
     * 
     * @param user
     * @param request
     * @return TRUE if created successfully, else FALSE
     */
    public Boolean create(UserClient user, PostRequest request);

    /**
     * Update object on server
     * 
     * @param user
     * @param request
     * @return TRUE if updated successfully, else FALSE
     */
    public Boolean update(UserClient user, PatchRequest request);

    /**
     * Delete object from server
     * 
     * @param user
     * @param request
     * @return TRUE if deleted successfully, else FALSE
     */
    public Boolean delete(UserClient user, DeleteRequest request);
}
