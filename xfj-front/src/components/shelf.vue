<template>
  <div class="gray-box" v-if="show">
    <div class="title">
      <h2>{{title}}</h2>
      <div>
        <slot name="right"></slot>
      </div>
    </div>
    <!--内容-->
    <div>
      <slot name="content"></slot>
    </div>
  </div>
</template>
<script src="../js/components/shelf.js"></script>
<style src="../css/components/shelf.scss" lang="scss" rel="stylesheet/scss" scoped></style>
