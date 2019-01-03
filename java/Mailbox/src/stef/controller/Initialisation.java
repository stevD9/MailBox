package stef.controller;

import java.util.ArrayList;
import stef.dao.DraftDao;
import stef.dao.InboxDao;
import stef.dao.MessageDao;
import stef.dao.SentDao;
import stef.dao.TrashDao;
import stef.dao.UserDao;
import stef.model.Draft;
import stef.model.Inbox;
import stef.model.Message;
import stef.model.Sent;
import stef.model.Trash;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class Initialisation {
    
    private UserDao userDao = new UserDao();
    private MessageDao messageDao = new MessageDao();
    private InboxDao inboxDao = new InboxDao();
    private SentDao sentDao = new SentDao();
    private TrashDao trashDao = new TrashDao();
    private DraftDao draftDao = new DraftDao();

    public ArrayList<User> getListOfAllUsers() {
        return userDao.getListOfAllUsers();
    }
    
    public ArrayList<Message> getListOfExistingMessages() {
        return messageDao.getListOfExistingMessages();
    }

    public ArrayList<Inbox> getListOfUserInbox(User user) {
        return inboxDao.getListOfUserInbox(user);
    }

    public ArrayList<Sent> getListOfUserSent(User user) {
        return sentDao.getListOfUserSent(user);
    }

    public ArrayList<Trash> getListOfUserTrash(User user) {
        return trashDao.getListOfUserTrash(user);
    }

    public ArrayList<Draft> getListOfUserDraft(User user) {
        return draftDao.getListOfUserDraft(user);
    }

}
