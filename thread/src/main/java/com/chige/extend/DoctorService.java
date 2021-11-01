package com.chige.extend;

import java.util.List;

/**
 * @author wangyc
 * @date 2021/9/26
 */
public abstract class DoctorService implements FollowService {

    /**
     *  判断用户与一批用户之间的关注状态（四种）
     * @return
     */
    protected abstract List<Integer> findFollowStatus();

    /**
     *  查询用户与一批用户之间的共同好友
     * @return
     */
    protected abstract List<Object> queryCommonFriendsBatch();

    public Integer addFollow(Integer personId, Integer linkPersonId) {
        return null;
    }

}
