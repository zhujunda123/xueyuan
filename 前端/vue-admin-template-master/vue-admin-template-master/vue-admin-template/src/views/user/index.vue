<template>

  <div>
    <!-- 用户列表
        <ul v-for="item in userlist" :key="item.id" >
          <li> {{item.username}} </li>
          <li> {{item.password}}</li>
        </ul> -->
    <p
      v-if="roles=='[guest]'"
      style="text-align:center;font-size:18px;"
    >无权限访问</p>
    <br>
    &nbsp;&nbsp;<el-button
      v-if="roles=='[admin]'"
      type="primary"
      size="mini"
      icon="el-icon-edit"
      @click="dialogVisibleadd = true;username1='';password1='';"
    >添加帐号</el-button>
    <br>
    <template>

      <el-table
        :data="userlist"
        style="width: 100%"
        v-if="roles=='[admin]'"
      >

        <el-table-column
          prop="id"
          label="id"
          width="180"
        >
        </el-table-column>

        <el-table-column
          prop="username"
          label="用户"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="password"
          label="密码"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">

            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              @click="dialogVisible = true;getData(scope.row.id)"
            >修改</el-button>

            <el-button
              v-if="roles=='[admin]'"
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="removeDataById(scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog
        title="修改"
        :visible.sync="dialogVisible"
        width="30%"
      >

        <span>
          帐号：<el-input
            v-model="username"
            placeholder="帐号"
          > </el-input> <br>
          密码：<el-input
            v-model="password"
            placeholder="密码"
          ></el-input>

        </span>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="dialogVisible = false;updata()"
          >确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
        title="添加"
        :visible.sync="dialogVisibleadd"
        width="30%"
      >

        <span>
          帐号：<el-input
            v-model="username1"
            placeholder="帐号"
          > </el-input> <br>
          密码：<el-input
            v-model="password1"
            placeholder="密码"
          ></el-input>

        </span>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisibleadd = false">取 消</el-button>
          <el-button
            type="primary"
            @click="dialogVisibleadd = false;saveData()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </template>

  </div>
</template>
<script>
import user from '@/api/user'
import { getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
export default {
  data () {
    return {
      dialogVisible: false,
      dialogVisibleadd: false,
      userlist: [],
      id: '',
      username: '',
      password: '',
      username1: '',
      password1: '',
      user: {},
      roles: [],
    }
  },
  created () {
    this.getUser()


    getInfo(getToken()).then(response => {
      this.roles = response.data.roles
      console.log(response.data.roles)
      console.log(this.roles)
    })

  },
  methods: {
    getUser () {
      user.getUserList()
        .then(
          response => {
            //    console.log(response)
            this.userlist = response.data.list;
          }
        ).catch(
          response => {
            console.log(response)
          }
        )
    },
    removeDataById (id) {

      user.removeUserbyId(id)
        .then(() => {
          this.getUser()
          return this.$message({
            type: 'success',
            message: '修改成功!'
          })
        }).then(() => {
          this.$router.push({ path: '/user/index' })
        })
        .catch()

    },
    getData (id) {

      user.getUserById(id).then(
        response => {
          // console.log(response)
          this.id = response.data.user.id
          this.username = response.data.user.username
          this.password = response.data.user.password

        }
      )
    },
    updata () {
      //    console.log(this.id)
      //    console.log(this.username)
      //      console.log(this.password)
      this.user.id = this.id
      this.user.username = this.username
      this.user.password = this.password
      // console.log(this.user)
      user.upDataById(this.id, this.user)
        .then(() => {
          this.getUser()
          return this.$message({
            type: 'success',
            message: '修改成功!'
          })
        }).then(() => {
          this.$router.push({ path: '/user/index' })
        }).catch(
          () => {
            console.log("失败")
          }
        )
    },
    saveData () {

      this.user.username = this.username1
      this.user.password = this.password1
      console.log(this.user)
      user.saveUser(this.user).then(() => {
        this.getUser()
        return this.$message({
          type: 'success',
          message: '修改成功!'
        })
      })
        .then(() => {
          this.$router.push({ path: '/user/index' })
        }).catch(
          () => {
            console.log("失败")
          }
        )
    },
  }
}
</script>

