package OopHW.Exp1.domain.devices.smartLock;

public interface LockStatusController {
    LockStatusType getLockStatus();
    void setLockStatus(LockStatusType lockStatus) ;

    enum LockStatusType {
        LOCK,
        UNLOCK
    }
}
