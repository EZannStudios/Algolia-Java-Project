package com.challenge.module.testutils;

public class JsonFileReader {

    public static String getMockedJsonStream() {
        return """
                {
                  "exhaustive": {
                    "nbHits": false,
                    "typo": false
                  },
                  "exhaustiveNbHits": false,
                  "exhaustiveTypo": false,
                  "hits": [
                    {
                      "_highlightResult": {
                        "author": {
                          "matchLevel": "none",
                          "matchedWords": [],
                          "value": "ExampleAuthor"
                        },
                        "comment_text": {
                          "fullyHighlighted": false,
                          "matchLevel": "full",
                          "matchedWords": [
                            "java"
                          ],
                          "value": "&quot;45% adoption&quot; doesn't tell about just how poorly IPv6 is actually supported on routers and home devices \\u003Cem\\u003Eava\\u003C/em\\u003Eilable to smaller customers."
                        },
                        "story_title": {
                          "matchLevel": "none",
                          "matchedWords": [],
                          "value": "Why No IPv6?"
                        },
                        "story_url": {
                          "matchLevel": "none",
                          "matchedWords": [],
                          "value": "https://whynoipv6.com/"
                        }
                      },
                      "_tags": [
                        "comment",
                        "author_ExampleAuthor",
                        "story_40039154"
                      ],
                      "author": "ExampleAuthor",
                      "comment_text": "&quot;45% adoption&quot; doesn&#x27;t tell about just how poorly IPv6 is actually supported on routers and home devices available to smaller customers.",
                      "created_at": "2024-04-15T20:55:16Z",
                      "created_at_i": 1713214516,
                      "objectID": "40045449",
                      "parent_id": 40041966,
                      "story_id": 40039154,
                      "story_title": "Why No IPv6?",
                      "story_url": "https://whynoipv6.com/",
                      "updated_at": "2024-04-15T20:59:51Z"
                    },
                    {
                      "_highlightResult": {
                        "author": {
                          "matchLevel": "none",
                          "matchedWords": [],
                          "value": "tim333"
                        },
                        "comment_text": {
                          "fullyHighlighted": false,
                          "matchLevel": "full",
                          "matchedWords": [
                            "java"
                          ],
                          "value": "Wikipedia says &quot;... a petawatt-scale laser pulse could launch an '\\u003Cem\\u003Eava\\u003C/em\\u003Elanche' fusion reaction.&quot; Quite a lot of zeros to add on to the power.\\u003Cp\\u003EThe article says they are planning 10kW or 10^4W and a petawatt is 10^15W so 11 zeros by my calculation. Could be a while. I think Helion, who are now talking about turning on their fusion to electricity gizmo this summer, may get there first."
                        },
                        "story_title": {
                          "matchLevel": "none",
                          "matchedWords": [],
                          "value": "A tiny ultrabright laser that can melt steel"
                        },
                        "story_url": {
                          "matchLevel": "none",
                          "matchedWords": [],
                          "value": "https://spectrum.ieee.org/pcsel"
                        }
                      },
                      "_tags": [
                        "comment",
                        "author_tim333",
                        "story_40038251"
                      ],
                      "author": "tim333",
                      "comment_text": "Wikipedia says &quot;... a petawatt-scale laser pulse could launch an &#x27;avalanche&#x27; fusion reaction.&quot; Quite a lot of zeros to add on to the power.\\u003Cp\\u003EThe article says they are planning 10kW or 10^4W and a petawatt is 10^15W so 11 zeros by my calculation. Could be a while. I think Helion, who are now talking about turning on their fusion to electricity gizmo this summer, may get there first.",
                      "created_at": "2024-04-15T20:53:07Z",
                      "created_at_i": 1713214387,
                      "objectID": "40045424",
                      "parent_id": 40040508,
                      "story_id": 40038251,
                      "story_title": "A tiny ultrabright laser that can melt steel",
                      "story_url": "https://spectrum.ieee.org/pcsel",
                      "updated_at": "2024-04-15T21:01:06Z"
                    }
                  ],
                  "hitsPerPage": 20,
                  "nbHits": 522254,
                  "nbPages": 50,
                  "page": 0,
                  "params": "query=java&advancedSyntax=true&analyticsTags=backend",
                  "processingTimeMS": 4,
                  "processingTimingsMS": {
                    "_request": {
                      "roundTrip": 17
                    },
                    "afterFetch": {
                      "format": {
                        "highlighting": 1,
                        "total": 1
                      }
                    },
                    "fetch": {
                      "query": 2,
                      "total": 3
                    },
                    "total": 4
                  },
                  "query": "java",
                  "serverTimeMS": 5
                }""";
    }
}
