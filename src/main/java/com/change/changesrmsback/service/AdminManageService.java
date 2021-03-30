package com.change.changesrmsback.service;

import com.change.changesrmsback.entity.Admin;
import com.change.changesrmsback.entity.Page;
import com.change.changesrmsback.mapper.AdminMapper;
import com.change.changesrmsback.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员管理的业务逻辑
 * @author Change
 */
@Service
public class AdminManageService {

    /** 管理员表admin的查询 */
    private AdminMapper adminMapper;

    /** 管理员表admin查询的自动注入 */
    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /**
     * 查询管理员列表，可选择进行分页或者模糊查询，模糊查询只模糊名称adminName和姓名adminNockname
     * @param page  如果选择分页，那么pageNum和pageSize属性不能为空
     * @param query 如果选择模糊查询，可传入，若为空则不模糊
     * @return 将查询的结果封装进map，包括adminList场地列表和总数total
     */
    public Map<String, Object> adminList(Page page, String query) {
        // 计算查询分行的起始
        page.setPageStart((page.getPageNum() - 1) * page.getPageSize());
        // 整合模糊查询的通配符%，查询
        List<Admin> adminList = adminMapper.selectAdminList(page, "%" + query + "%");
        // 总数
        int total = adminMapper.selectAdminCount("%" + query + "%");
        // 封装和返回
        Map<String, Object> result = new HashMap<>();
        result.put("adminList", adminList);
        result.put("total", total);
        return result;
    }

    /**
     * 添加admin的方法，会使用雪花算法生成id
     * @param admin 至少包括adminName、adminNickname、adminPassword和role
     * @throws Exception 传入的数据有误时抛出异常，操作数据库失败时抛出异常
     */
    public void addAdmin(Admin admin) throws Exception {
        // 数据验证
        if (admin.getAdminName() == null || "".equals(admin.getAdminName())) {
            throw new Exception("用户名不能为空");
        }
        if (admin.getAdminName().length() > 18 || admin.getAdminName().length() < 4) {
            throw new Exception("用户名长度在4到18个字符");
        }
        if (admin.getAdminNickname() == null || "".equals(admin.getAdminNickname())) {
            throw new Exception("姓名不能为空");
        }
        if (admin.getAdminNickname().length() > 12) {
            throw new Exception("姓名最多为12个字符");
        }
        if (admin.getAdminPassword() == null || "".equals(admin.getAdminPassword())) {
            throw new Exception("密码不能为空");
        }
        if (admin.getAdminPassword().length() > 16 || admin.getAdminPassword().length() < 6) {
            throw new Exception("密码长度在6到16个字符");
        }
        if (!"general".equals(admin.getRole()) && !"super".equals(admin.getRole())) {
            throw new Exception("角色错误");
        }
        // 添加id
        admin.setId(new SnowflakeIdWorker(0, 0).nextId());
        int result = adminMapper.insertOneAdmin(admin);
        if (result != 1) {
            throw new Exception("添加失败，请重试");
        }
    }

    /**
     * 编辑admin的方法
     * @param admin 至少包括id、adminName、role和version
     * @throws Exception 传入的数据有误时抛出异常，操作数据库失败时抛出异常
     */
    public void editAdmin(Admin admin) throws Exception {
        // 数据验证
        if (admin.getId() == 0) {
            throw new Exception("id不能为空");
        }
        if (admin.getAdminNickname() == null || "".equals(admin.getAdminNickname())) {
            throw new Exception("姓名不能为空");
        }
        if (admin.getAdminNickname().length() > 12) {
            throw new Exception("姓名最多为12个字符");
        }
        if (!"general".equals(admin.getRole()) || !"super".equals(admin.getRole())) {
            throw new Exception("角色错误");
        }
        // 操作
        int result = adminMapper.updateOneAdmin(admin);
        if (result != 1) {
            throw new Exception("添加失败，请重试");
        }
    }

    /**
     * 删除admin的方法
     * @param id      要删除的admin的id
     * @param version 数据库的乐观锁标识，防止修改时冲突
     * @throws Exception 传入的id有误时抛出异常，操作数据库失败时抛出异常
     */
    public void deleteAdmin(Long id, int version) throws Exception {
        // 数据验证
        if (id == 0) {
            throw new Exception("id不能为空");
        }
        // 操作
        int result = adminMapper.deleteOneAdmin(id, version);
        if (result != 1) {
            throw new Exception("删除失败，请重试");
        }
    }
}
