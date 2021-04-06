package com.change.changesrmsback.mapper;

import com.change.changesrmsback.entity.History;
import com.change.changesrmsback.entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据访问层，对接数据库history表
 * @author Change
 */
@Mapper
public interface HistoryMapper {

    /**
     * 插入一个历史
     * @param history 要插入的场地，包括id、siteName、location、seat、hasKeys
     * @return 成功返回1，不成功返回0
     */
    int insertOneHistory(History history);

    /**
     * 根据userId查询属于他的申请历史
     * @param page   分页，可为空
     * @param userId 要查询的userId
     * @return 返回查询结果
     */
    List<History> selectHistoryListByUserId(@Param("page") Page page, @Param("userId") long userId);

    /**
     * 根据userId查询属于他的申请历史的总数
     * @param id 要查询的userId
     * @return 符合条件的数目
     */
    int selectHistoryCountByUserId(long id);

    /**
     * 通过申请历史的id查询整个申请历史
     * @param id 要查询的id
     * @return 查询到的一整个申请历史
     */
    History selectOneHistory(long id);

    /**
     * 根据id删除一个申请历史
     * @param id      要删除的历史的id
     * @param version 对应数据库的乐观锁
     * @return 成功返回1，不成功返回0
     */
    int deleteOneHistory(@Param("id") Long id, @Param("version") int version);

    /**
     * 第一次审核，查询所需要审核的列表
     * @param page 分页，可为空
     * @return 查询到的列表
     */
    List<History> selectFirstCheck(Page page);

    /**
     * 第一次审核，查询所需要审核的列表的数量
     * @return 查询到的数量
     */
    int selectFirstCheckCount();

    /**
     * 第一次审核提交
     * @param history 包含审核的id，管理员的id，审核后loandState的值，version
     * @return 成功返回1，不成功返回0
     */
    int firstCheckUpdate(History history);

    /**
     * 第二次审核，查询所需要审核的列表
     * @param page 分页，可为空
     * @return 查询到的列表
     */
    List<History> selectSecondCheck(Page page);

    /**
     * 第二次审核，查询所需要审核的列表的数量
     * @return 查询到的数量
     */
    int selectSecondCheckCount();

    /**
     * 第二次审核提交
     * @param history 包含审核的id，管理员的id，审核后loandState的值，version
     * @return 成功返回1，不成功返回0
     */
    int secondCheckUpdate(History history);

    /**
     * 查询要管理的钥匙列表
     * @param page 分页
     * @return 查询到的列表
     */
    List<History> selectKeyManageList(Page page);

    /**
     * 查询要管理的钥匙列表的总数
     * @return 查询到的数量
     */
    int selectKeyManageCount();

    /**
     * 借出钥匙，改变表
     * @param history 至少包含loanKeyId、id、version
     * @return 成功返回1，失败返回0
     */
    int loanKey(History history);

    /**
     * 归还钥匙，改变表
     * @param history 至少包含loanKeyId、id、version
     * @return 成功返回1，失败返回0
     */
    int returnKey(History history);

    /**
     * 查询今天、过去三天、以后三天的借用数量
     * @return 查询到的结果，按时间排序
     */
    List<Integer> selectSucceedNearThreeDays();
}
