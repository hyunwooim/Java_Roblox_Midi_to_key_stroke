package org.example;

import javax.sound.midi.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MidiToKeystrokeWithNoteName {
    public static void main(String[] args) {
        try {
            // 시스템의 모든 MIDI 입력 장치 검색
            MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();

            // 연결된 모든 MIDI 장치 중 첫 번째 입력 장치를 찾음
            MidiDevice inputDevice = null;
            for (MidiDevice.Info info : deviceInfo) {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getMaxTransmitters() != 0) {  // 입력 장치가 있을 경우
                    inputDevice = device;
                    break;
                }
            }

            if (inputDevice == null) {
                System.out.println("MIDI 입력 장치가 감지되지 않았습니다.");
                return;
            }

            // MIDI 장치를 열고 Receiver 설정
            inputDevice.open();
            inputDevice.getTransmitter().setReceiver(new MidiReceiver());

            System.out.println("MIDI 입력 대기 중...");

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    // MIDI 메시지 수신을 위한 Receiver 클래스
    static class MidiReceiver implements Receiver {
        Robot robot;

        public MidiReceiver() {
            try {
                robot = new Robot();  // 키보드 이벤트를 생성하기 위한 Robot 객체
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void send(MidiMessage message, long timeStamp) {
            if (message instanceof ShortMessage) {
                ShortMessage shortMessage = (ShortMessage) message;
                int note = shortMessage.getData1(); // 음 (Note Number)
                int velocity = shortMessage.getData2(); // 속도 (Velocity)

                // NOTE_ON 메시지 (음이 눌렸을 때)
                if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
                    System.out.println("Note ON: " + getNoteName(note) + " (Velocity: " + velocity + ")");
                    // MIDI 음을 키보드 키로 매핑해서 입력
                    mapNoteToKey(note, velocity, true);
                }

                // NOTE_OFF 메시지 (음이 떼졌을 때)
                if (shortMessage.getCommand() == ShortMessage.NOTE_OFF) {
                    System.out.println("Note OFF: " + getNoteName(note));
                    // MIDI 음을 키보드 키로 매핑해서 입력 종료
                    mapNoteToKey(note, velocity, false);
                }
            }
        }

        @Override
        public void close() {
            // Receiver 닫을 때 필요한 작업이 있으면 작성
        }

        // MIDI 음을 키보드 키에 매핑하는 함수
        private void mapNoteToKey(int note, int velocity, boolean isNoteOn) {
            int key = -1;
            int key2 = -1;
            // MIDI 음을 키보드 키에 매핑 (예시: 60 = C4 -> 'Q' 키)
            switch (note) {
                case 21: key = KeyEvent.VK_1; key2 = KeyEvent.VK_CONTROL; break;
                case 22: key = KeyEvent.VK_2; key2 = KeyEvent.VK_CONTROL; break;
                case 23: key = KeyEvent.VK_3; key2 = KeyEvent.VK_CONTROL; break;
                case 24: key = KeyEvent.VK_4; key2 = KeyEvent.VK_CONTROL; break;
                case 25: key = KeyEvent.VK_5; key2 = KeyEvent.VK_CONTROL; break;
                case 26: key = KeyEvent.VK_6; key2 = KeyEvent.VK_CONTROL; break;
                case 27: key = KeyEvent.VK_7; key2 = KeyEvent.VK_CONTROL; break;
                case 28: key = KeyEvent.VK_8; key2 = KeyEvent.VK_CONTROL; break;
                case 29: key = KeyEvent.VK_9; key2 = KeyEvent.VK_CONTROL; break;
                case 30: key = KeyEvent.VK_0; key2 = KeyEvent.VK_CONTROL; break;
                case 31: key = KeyEvent.VK_Q; key2 = KeyEvent.VK_CONTROL; break;
                case 32: key = KeyEvent.VK_W; key2 = KeyEvent.VK_CONTROL; break;
                case 33: key = KeyEvent.VK_E; key2 = KeyEvent.VK_CONTROL; break;
                case 34: key = KeyEvent.VK_R; key2 = KeyEvent.VK_CONTROL; break;
                case 35: key = KeyEvent.VK_T; key2 = KeyEvent.VK_CONTROL; break;
                case 36: key = KeyEvent.VK_1; key2 = -1; break;
                case 37: key = KeyEvent.VK_1; key2 = KeyEvent.VK_SHIFT; break;
                case 38: key = KeyEvent.VK_2; key2 = -1; break;
                case 39: key = KeyEvent.VK_2; key2 = KeyEvent.VK_SHIFT; break;
                case 40: key = KeyEvent.VK_3; key2 = -1; break;
                case 41: key = KeyEvent.VK_4; key2 = -1; break;
                case 42: key = KeyEvent.VK_4; key2 = KeyEvent.VK_SHIFT; break;
                case 43: key = KeyEvent.VK_5; key2 = -1; break;
                case 44: key = KeyEvent.VK_5; key2 = KeyEvent.VK_SHIFT; break;
                case 45: key = KeyEvent.VK_6; key2 = -1; break;
                case 46: key = KeyEvent.VK_6; key2 = KeyEvent.VK_SHIFT; break;
                case 47: key = KeyEvent.VK_7; key2 = -1; break;
                case 48: key = KeyEvent.VK_8; key2 = -1; break;
                case 49: key = KeyEvent.VK_8; key2 = KeyEvent.VK_SHIFT; break;
                case 50: key = KeyEvent.VK_9; key2 = -1; break;
                case 51: key = KeyEvent.VK_9; key2 = KeyEvent.VK_SHIFT; break;
                case 52: key = KeyEvent.VK_0; key2 = -1; break;
                case 53: key = KeyEvent.VK_Q; key2 = -1; break;
                case 54: key = KeyEvent.VK_Q; key2 = KeyEvent.VK_SHIFT; break;
                case 55: key = KeyEvent.VK_W; key2 = -1; break;
                case 56: key = KeyEvent.VK_W; key2 = KeyEvent.VK_SHIFT; break;
                case 57: key = KeyEvent.VK_E; key2 = -1; break;
                case 58: key = KeyEvent.VK_E; key2 = KeyEvent.VK_SHIFT; break;
                case 59: key = KeyEvent.VK_R; key2 = -1; break;
                case 60: key = KeyEvent.VK_T; key2 = -1; break;
                case 61: key = KeyEvent.VK_T; key2 = KeyEvent.VK_SHIFT; break;
                case 62: key = KeyEvent.VK_Y; key2 = -1; break;
                case 63: key = KeyEvent.VK_Y; key2 = KeyEvent.VK_SHIFT; break;
                case 64: key = KeyEvent.VK_U; key2 = -1; break;
                case 65: key = KeyEvent.VK_I; key2 = -1; break;
                case 66: key = KeyEvent.VK_I; key2 = KeyEvent.VK_SHIFT; break;
                case 67: key = KeyEvent.VK_O; key2 = -1; break;
                case 68: key = KeyEvent.VK_O; key2 = KeyEvent.VK_SHIFT; break;
                case 69: key = KeyEvent.VK_P; key2 = -1; break;
                case 70: key = KeyEvent.VK_P; key2 = KeyEvent.VK_SHIFT; break;
                case 71: key = KeyEvent.VK_A; key2 = -1; break;
                case 72: key = KeyEvent.VK_S; key2 = -1; break;
                case 73: key = KeyEvent.VK_S; key2 = KeyEvent.VK_SHIFT; break;
                case 74: key = KeyEvent.VK_D; key2 = -1; break;
                case 75: key = KeyEvent.VK_D; key2 = KeyEvent.VK_SHIFT; break;
                case 76: key = KeyEvent.VK_F; key2 = -1; break;
                case 77: key = KeyEvent.VK_G; key2 = -1; break;
                case 78: key = KeyEvent.VK_G; key2 = KeyEvent.VK_SHIFT; break;
                case 79: key = KeyEvent.VK_H; key2 = -1; break;
                case 80: key = KeyEvent.VK_H; key2 = KeyEvent.VK_SHIFT; break;
                case 81: key = KeyEvent.VK_J; key2 = -1; break;
                case 82: key = KeyEvent.VK_J; key2 = KeyEvent.VK_SHIFT; break;
                case 83: key = KeyEvent.VK_K; key2 = -1; break;
                case 84: key = KeyEvent.VK_L; key2 = -1; break;
                case 85: key = KeyEvent.VK_L; key2 = KeyEvent.VK_SHIFT; break;
                case 86: key = KeyEvent.VK_Z; key2 = -1; break;
                case 87: key = KeyEvent.VK_Z; key2 = KeyEvent.VK_SHIFT; break;
                case 88: key = KeyEvent.VK_X; key2 = -1; break;
                case 89: key = KeyEvent.VK_C; key2 = -1; break;
                case 90: key = KeyEvent.VK_C; key2 = KeyEvent.VK_SHIFT; break;
                case 91: key = KeyEvent.VK_V; key2 = -1; break;
                case 92: key = KeyEvent.VK_V; key2 = KeyEvent.VK_SHIFT; break;
                case 93: key = KeyEvent.VK_B; key2 = -1; break;
                case 94: key = KeyEvent.VK_B; key2 = KeyEvent.VK_SHIFT; break;
                case 95: key = KeyEvent.VK_N; key2 = -1; break;
                case 96: key = KeyEvent.VK_M; key2 = -1; break;
                case 97: key = KeyEvent.VK_Y; key2 = KeyEvent.VK_CONTROL; break;
                case 98: key = KeyEvent.VK_U; key2 = KeyEvent.VK_CONTROL; break;
                case 99: key = KeyEvent.VK_I; key2 = KeyEvent.VK_CONTROL; break;
                case 100: key = KeyEvent.VK_O; key2 = KeyEvent.VK_CONTROL; break;
                case 101: key = KeyEvent.VK_P; key2 = KeyEvent.VK_CONTROL; break;
                case 102: key = KeyEvent.VK_A; key2 = KeyEvent.VK_CONTROL; break;
                case 103: key = KeyEvent.VK_S; key2 = KeyEvent.VK_CONTROL; break;
                case 104: key = KeyEvent.VK_D; key2 = KeyEvent.VK_CONTROL; break;
                case 105: key = KeyEvent.VK_F; key2 = KeyEvent.VK_CONTROL; break;
                case 106: key = KeyEvent.VK_G; key2 = KeyEvent.VK_CONTROL; break;
                case 107: key = KeyEvent.VK_H; key2 = KeyEvent.VK_CONTROL; break;
                case 108: key = KeyEvent.VK_J; key2 = KeyEvent.VK_CONTROL; break;
                // A4 (A4 -> 'P')
                // 필요에 따라 더 많은 음을 추가 가능
            }

            // 키보드 키 매핑을 통한 입력 시뮬레이션
            if (key != -1) {
                if (isNoteOn) {
                    // 키를 누르는 동작 (NOTE_ON)
                    if (key2 != -1){
                        robot.keyPress(key2);
                    }
                    robot.keyPress(key);

                } else {
                    // 키를 떼는 동작 (NOTE_OFF)
                    robot.keyRelease(key);

                    if (key2 != -1){
                        robot.keyRelease(key2);
                    }
                }
            }
        }

        // 음 번호에 해당하는 음 이름을 반환하는 함수
        private String getNoteName(int note) {
            String[] noteNames = {
                    "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
            };

            // 음 번호는 0부터 127까지 존재 (예: 60은 C4)
            int octave = (note / 12) - 1; // 옥타브 계산
            int noteIndex = note % 12;    // 0 ~ 11 범위 내에서 음 계산

            return noteNames[noteIndex] + octave;
        }
    }
}



