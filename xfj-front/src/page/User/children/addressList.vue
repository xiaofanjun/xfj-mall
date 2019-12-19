<template>
  <div>
    <y-shelf title="收货地址">
      <span slot="right"><y-button text="添加收货地址" style="margin: 0" @btnClick="addNewAddress()"></y-button></span>
      <div slot="content">
        <!--标题-->
        <div class="table-title">
          <span class="name">姓名</span> <span class="address">详细地址</span> <span class="tel">电话</span>
        </div>
        <div v-if="addList.length">
          <div class="address-item" v-for="(item,i) in addList" :key="i">
            <div class="name">{{item.userName}}</div>
            <div class="address-msg">{{item.streetName | addressFilter}}</div>
            <div class="telephone">{{item.tel}}</div>
            <div class="defalut">
              <a @click="changeDef(item)"
                 href="javascript:;"
                 v-text="item.isDefault?'( 默认地址 )':'设为默认'"
                 :class="{'defalut-address':item.isDefault}"></a>
            </div>
            <div class="operation">
              <el-button type="primary" icon="edit" size="small" @click="update(item)"></el-button>
              <el-button type="danger" icon="delete" size="small" :data-id="item.addressId"
                         @click="del(item.addressId,i)"></el-button>
            </div>
          </div>
        </div>
        <div v-else>
          <div style="padding: 80px 0;text-align: center">
            <div style="font-size: 20px">你还未添加收货地址</div>
            <div style="margin: 20px ">
              <y-button text="添加地址" @btnClick="update()"></y-button>
            </div>
          </div>
        </div>
      </div>
    </y-shelf>
    <y-popup :open="popupOpen" @close='popupOpen=false' :title="popupTitle">
      <div slot="content" class="md" :data-id="msg.addressId">
        <div>
          <el-input type="text" placeholder="收货人姓名" v-model="msg.userName"></el-input>
        </div>
        <div>
          <el-input type="number" placeholder="手机号码" v-model="msg.tel"></el-input>
        </div>
        <!--      数据源 https://github.com/wecatch/china_regions/tree/master/json    -->
        <!--        TODO 地址模块的同学mysql创建字典表-->
        <!--        <div>-->
        <!--          <input type="text" placeholder="收货地址" v-model="msg.streetName">-->
        <!--        </div>-->
        <div style="display: flex;flex-direction: row">
          <div style="flex: 1;">
            <el-select @change="_handleProvinceChange" v-model="provinceId" placeholder="请选择省份">
              <el-option
                v-for="(item, index) in provinceList"
                :key="index"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </div>
          <div style="flex: 1;padding-left:4px">
            <el-select @change="_handleCityChange" v-model="cityId" placeholder="请选择市">
              <el-option
                v-for="(item, index) in cityList"
                :key="index"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </div>
        </div>
        <div style="display: flex;">
          <el-select style="flex: 1" @change="_handleDistrictChange" v-model="districtId" placeholder="请选择区">
            <el-option
              v-for="(item, index) in districtList"
              :key="index"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </div>
        <div>
          <el-input type="text" placeholder="详细地址" @change="_handleAddressChange" v-model="address"></el-input>
        </div>
        <div>
          <el-checkbox class="auto-login" v-model="msg._Default">设为默认</el-checkbox>
        </div>
        <y-button text='保存'
                  class="btn"
                  :classStyle="btnHighlight?'main-btn':'disabled-btn'"
                  @btnClick="save({userId:userId,addressId:msg.addressId,userName:msg.userName,tel:msg.tel,streetName:msg.streetName,_Default:msg._Default})">
        </y-button>
      </div>
    </y-popup>
  </div>
</template>
<script src="../../../js/user/children/addresslist.js"></script>
<style src="../../../css/user/children/addresslist.scss" scoped lang="scss"></style>

