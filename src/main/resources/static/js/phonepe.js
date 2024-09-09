
const settings = {
    async: true,
    crossDomain: true,
    url: 'https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay',
    method:  'post',
    headers: {
      accept: 'text/plain',
      
   'Content-Type': 'application/json ',
   'X-VERIFY': '2033cf2152d3983077baf662c8ed095dd81fae3489685c2a4e4776a1d7b18ce9###1'
      },
      processData: false,
      data: '{"request": "ewoibWVyY2hhbnRJZCI6ICJQR1RFU1RQQVlVQVQ4NiIsCiAgIm1lcmNoYW50VHJhbnNhY3Rpb25JZCI6ICJNVDc4NTA1OTAwNjgxODgxMDQiLAogICJtZXJjaGFudFVzZXJJZCI6ICJNVUlEMTIzIiwKICAiYW1vdW50IjogMTAwLAogICJyZWRpcmVjdFVybCI6ICJodHRwczovL3dlYmhvb2suc2l0ZS9yZWRpcmVjdC11cmwiLAogICJyZWRpcmVjdE1vZGUiOiAiUkVESVJFQ1QiLAogICJjYWxsYmFja1VybCI6ICJodHRwczovL3dlYmhvb2suc2l0ZS9jYWxsYmFjay11cmwiLAogICJtb2JpbGVOdW1iZXIiOiAiOTk5OTk5OTk5OSIsCiAgInBheW1lbnRJbnN0cnVtZW50IjogewogICAgInR5cGUiOiAiUEFZX1BBR0UiCiAgfQp9"}'
      };
            
  $.ajax(settings).done(function (response) {
    console.log(response);
  });