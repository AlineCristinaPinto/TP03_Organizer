$(function() {
  
  $(".btOption").click(function() {
      let idItem = $(this).attr('id');
      idItem = idItem.substr(0,(idItem.length - 2));
      document.querySelector("#takeId").value=idItem;
      document.querySelector("#takeIdU").value=idItem;
      document.querySelector("#takeTypeU").value=$(this).attr('value');
  });

  $(".delItem").click(function() {
      let formRegister = document.querySelector("#deleteItem");
      formRegister.action = "/organizer/servletcontroller?process=DeleteItem";
      formRegister.submit();
  });
  
  $(".checkTar").click(function() {
      let idItem = $(this).attr('id');
      document.querySelector("#takeId").value=idItem;
      let formRegister = document.querySelector("#deleteItem");
      formRegister.action = "/organizer/servletcontroller?process=ConcludeTarefa";
      formRegister.submit();
  });

  $(".edit").click(function() {
      let formRegister = document.querySelector("#updateItem");
      formRegister.action = "/organizer/servletcontroller?process=ShowUpdateItem";
      formRegister.submit();
  });

  $(".checkTarC").click(function() {
      let idItem = $(this).attr('id');
      document.querySelector("#takeId").value=idItem;
      let formRegister = document.querySelector("#deleteItem");
      formRegister.action = "/organizer/servletcontroller?process=ChangeTarefaStatus";
      formRegister.submit();
  });

  $("#indexPage").click(function() {
      let formRegister = document.querySelector("#indexPageForm");
      formRegister.submit();
  });

})
