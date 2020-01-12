<template>
  <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column
      label="创建时间"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.CREATE_TIME}}</span>
      </template>
    </el-table-column>
     <el-table-column
      label="数据库表"
      width="180">
      <template slot-scope="scope">
     
        <span style="margin-left: 10px">{{ scope.row.TABLE_NAME}}</span>
      </template>
    </el-table-column>
   
    <el-table-column label="操作">
      <template slot-scope="scope">
    
        <el-button
          size="mini"
          type="danger"
          @click="handleSave(scope.$index, scope.row.TABLE_NAME)">备份</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import table from '@/api/table'
  export default {
    data() {
      return {
        tableData: [],
        tableName:"",
      }
    },
    created(){
            this.getAllTable()
    },
    
    methods: {
        getAllTable(){
                table.getList().then((response)=>{
                        // console.log(response)
                        this.tableData=response.data.list
                        console.log( this.tableData)
                }).catch()
        },
    
      handleSave(index, row) {
        // console.log(index, row);
        this.tableName=row
        console.log(this.tableName)
       table.backup(this.tableName).then(
         ()=>{
             this.$message({
                type: 'success',
                message: '备份成功!'
            })
         }
       ).catch(
         ()=>{
             this.$message({
                type: 'error',
                message: '备份失败!'
            })
         }
       )
      }
    }
  }
</script>
