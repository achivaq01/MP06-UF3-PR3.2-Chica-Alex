declare namespace output = "http://www.w3.org/2010/xslt-xquery-serialization";
declare option output:method 'json';

let $result :=
  for $question in //posts/row
  let $views := xs:integer($question/@ViewCount)
  where $question/@PostTypeId = 1
  order by $views descending
  return map {
    "id": data($question/@Id),
    "postTypeId": data($question/@PostTypeId),
    "acceptedAnswerId": data($question/@AcceptedAnswerId),
    "creationDate": data($question/@CreationDate),
    "viewCount": data($question/@ViewCount),
    "ownerUserId": data($question/@OwnerUserId),
    "lastActivityDate": data($question/@LastActivityDate),
    "tags": data($question/@Tags),
    "answerCount": data($question/@AnswerCount),
    "commentCount": data($question/@CommentCount),
    "score": data($question/@Score),
    "title": data($question/@Title),
    "body": data($question/@Body)
  }

return array { $result }
