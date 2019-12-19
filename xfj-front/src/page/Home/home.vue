<template>
  <div class="home">

    <div v-loading="loading" element-loading-text="加载中..." style="min-height: 35vw;" v-if="!error">
      <div class="banner">
        <div class="bg" ref="bg"
             @mouseover="bgOver($refs.bg)" @mousemove="bgMove($refs.bg,$event)" @mouseout="bgOut($refs.bg)">
          <transition name="fade">
            <div v-for="(item, i) in banner" v-if="i===mark" :key="i" style="position:absolute" @click="linkTo(item)"
                 @mouseover="stopTimer" @mouseout="startTimer">
              <img v-if="item.picUrl" class="img1" :src="item.picUrl"/>
              <img v-if="item.picUrl2" class="img2 a" :src="item.picUrl2"/>
              <img v-if="item.picUrl3" class="img3 b" :src="item.picUrl3"/>
            </div>
          </transition>
        </div>
        <div class="page">
          <ul class="dots">
            <li class="dot-active" v-for="(item, i) in banner" :class="{ 'dot':i!=mark }" :key="i"
                @click="change(i)"></li>
          </ul>
        </div>
      </div>

      <div v-for="(item,i) in home" :key="i">

        <div class="activity-panel" v-if="item.type === 1">
          <ul class="box">
            <li class="content" v-for="(iitem,j) in item.panelContentItems" :key="j" @click="linkTo(iitem)">
              <img class="i" :src="iitem.picUrl">
              <a class="cover-link"></a>
            </li>
          </ul>
        </div>

        <section class="w mt30 clearfix" v-if="item.type === 2">
          <y-shelf :title="item.name">
            <div slot="content" class="hot">
              <mall-goods :msg="iitem" v-for="(iitem,j) in item.panelContentItems" :key="j"></mall-goods>
            </div>
          </y-shelf>
        </section>

        <section class="w mt30 clearfix" v-if="item.type === 3">
          <y-shelf :title="item.name">
            <div slot="content" class="floors">
              <div class="imgbanner" v-for="(iitem,j) in item.panelContentItems" :key="j"
                   v-if="iitem.type === 2 || iitem.type === 3" @click="linkTo(iitem)">
                <img v-lazy="iitem.picUrl">
                <a class="cover-link"></a>
              </div>
              <mall-goods :msg="iitem" v-for="(iitem,j) in item.panelContentItems" :key="j+'key'"
                          v-if="iitem.type != 2 && iitem.type != 3"></mall-goods>
            </div>
          </y-shelf>
        </section>

      </div>
    </div>

    <div class="no-info" v-if="error">
      <div class="no-data">
        <img src="/static/images/error.png">
        <br> 抱歉！出错了...
      </div>
    </div>

    <!--<el-dialog
      title="通知"
      :visible.sync="dialogVisible"
      width="30%"
      style="width:70%;margin:0 auto">
      <span>测试消息</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">知道了</el-button>
      </span>
    </el-dialog>-->
  </div>
</template>
<script src="../../js/home/home.js"></script>
<style src="../../css/home/home.scss" lang="scss" rel="stylesheet/scss" scoped></style>
