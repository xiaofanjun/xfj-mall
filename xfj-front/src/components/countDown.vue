<template>
  <span :endTime="endTime" :callback="callback" :endText="endText">
    <slot>
      {{content}}
    </slot>
  </span>
</template>
<script src="../js/components/countdown.js"></script>
<style src="../css/components/countdown.scss" lang='scss' rel='stylesheet/scss' scoped></style>
