package com.account.pojo;

import java.util.List;

public interface MaintainMapper {
    Maintain selectById(int id);
    int selectcompet(int id);
    List<Maintain> selectAllMaintain(String updateUser);
    void insertMaintain(Maintain maintain);
    void deleteMaintain(int id);
    List<Maintain> selectAllMaintainstatus();
    void updateMaintain(int id);
    void updateMaintainUnDone(int id);
}
