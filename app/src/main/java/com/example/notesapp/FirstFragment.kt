package com.example.notesapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val TAG = "FirstFragment"
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notes = listOf<Note>(Note("Buy applets","I go supermarket and buy applets for launch"))
        var query = db.collection("notas").addSnapshotListener(EventListener{ documentSnapshots, e ->
            if(e != null){
                Log.d(TAG,"Fallo!!!",e)
                return@EventListener
            }
            val noteList = mutableListOf<Note>()
            for (doc in documentSnapshots!!){
                val n = doc.toObject(Note::class.java)
                n.uid = doc.id
                noteList.add(n)
            }
        })
        rvNotas.layoutManager = LinearLayoutManager(this.context)
        rvNotas.adapter = NoteRecyclerViewAdapter(this.requireContext(), notes);

        /*view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }
}