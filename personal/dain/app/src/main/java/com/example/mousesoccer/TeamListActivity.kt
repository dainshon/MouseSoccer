package com.example.mousesoccer

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mousesoccer.databinding.ActivityTeamListBinding
import org.json.JSONObject

class TeamListActivity : AppCompatActivity() {
    lateinit var binding: ActivityTeamListBinding
    lateinit var adapter: TeamAdapter
    lateinit var League : String

    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        League = "PL"
        init()
        //initRecycler()
    }

    fun init() {
        myDBHelper = MyDBHelper(this)

        var LeagueArray = listOf("리그를 선택하세요","BL1","BSA","CL","CLI","DED","ELC","FL1","PD", "PL","PPL","SA","WC")
        var Leagueadapter = ArrayAdapter<String>(this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item, LeagueArray)
        binding.spinner.adapter = Leagueadapter
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Log.d("selected", LeagueArray.get(position)+"선택")
                if(position == 0){
                    League = "PL"
                }else{
                    League = LeagueArray.get(position)
                }

                initRecycler()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        //array.xml 이용 방법
//        binding.spinner.adapter = ArrayAdapter.createFromResource(this, R.array.LeagueList, android.R.layout.activity_list_item)
//        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                when(position){
//                    0->{    //선택X
//                        League = "PL"
//                    }
//                    1->{    //BL1
//                        League = "BL1"
//                    }
//                    2->{    //BSA
//                        League = "BSA"
//                    }
//                    3->{    //CL
//                        League = "CL"
//                    }
//                    4->{    //CLI
//                        League = "CLI"
//                    }
//                    5->{    //DED
//                        League = "DED"
//                    }
//                    6->{    //ELC
//                        League = "ELC"
//                    }
//                    7->{    //FL1
//                        League = "FL1"
//                    }
//                    8->{    //PD
//                        League = "PD"
//                    }
//                    9->{    //PL
//                        League = "PL"
//                    }
//                    10->{    //PPL
//                        League = "PPL"
//                    }
//                    11->{    //SA
//                        League = "SA"
//                    }
//                    12->{    //WC
//                        League = "WC"
//                    }
//                }
//                initRecycler()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Log.d("tag","nothing selected")
//            }
//        }
       }



    fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = TeamAdapter(ArrayList<TeamData>())

        adapter.itemClickListener = object: TeamAdapter.OnItemClickListener{
            override fun OnItemClick(data: TeamData) {
                data.isSelected = !data.isSelected
                Log.d("click", "Activity")
                if(data.isSelected){
                    val league = League
                    val teamname = data.teamname
                    val myteam = MyTeamData(league, teamname)
                    val result = myDBHelper.insertData(myteam)
                    Log.d("clicked_result", result.toString())
                //teamImg.setImageResource(R.drawable.ic_baseline_star_24)
                }

                //커서 사용
                val cursor:Cursor
                cursor =
            }
        }
        binding.recyclerView.adapter = adapter
        val filename = "Team_"+League+".json"

        val jsonString = assets.open(filename).reader().readText()
        val jsonObject = JSONObject(jsonString)
        val standings_array = jsonObject.getJSONArray("standings").getJSONObject(0)     //type=TOTAL인 {}
        val team_array = standings_array.getJSONArray("table")  //team array

        var i=0
        var str = ""

        while(i < team_array.length()){
            val teamname = team_array.getJSONObject(i).getJSONObject("team").getString("name")
            val position = team_array.getJSONObject(i).getString("position").toInt()
            val teamimg = team_array.getJSONObject(i).getJSONObject("team").getString("crest")
            Log.d("img", teamimg)

            adapter.items.add(TeamData(teamname, position, teamimg))
            i++
        }

        //binding.textView.text = teamname
    }
}